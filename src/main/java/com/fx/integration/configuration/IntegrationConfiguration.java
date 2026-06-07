package com.fx.integration.configuration;

import com.fx.dto.jpa.StreetFood;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;


// gateway( set headers ) -> splitter(List<Order> -> Order) + -> filter(ingredient > 3) + -> router(vegan | not vegan from header)
// -> activator( sout different prefix ) +
@Configuration
@EnableIntegration
public class IntegrationConfiguration {

    @MessagingGateway(defaultRequestChannel = "streetFoodChannel")
    public interface GatewaySoutIntegration {
        void soutGate(List<StreetFood> streetFoodList);
    }

    @Bean
    public MessageChannel streetFoodChannel() {
        return new DirectChannel();
    }

    @Splitter(inputChannel = "streetFoodChannel", outputChannel = "streetFoodChannelSplitted")
    public List<StreetFood> splitStreetFoodList(List<StreetFood> streetFoodList) {
        return streetFoodList;
    }

    @Bean
    public MessageChannel streetFoodChannelSplitted() {
        return new DirectChannel();
    }

    @Filter(inputChannel = "streetFoodChannelSplitted", outputChannel = "streetFoodFiltered")
    public boolean filterLessThanThreeIngredients(StreetFood streetFood) {
        return streetFood.getIngredient().size() >= 3;
    }

    @Bean
    public MessageChannel streetFoodFiltered() {
        return new DirectChannel();
    }

    @Transformer(inputChannel = "streetFoodFiltered", outputChannel = "streetFoodHeaderTransformed")
    public Message<StreetFood> headerMaker(StreetFood streetFood) {
        boolean hasChickenMeat = streetFood.getIngredient().stream().anyMatch(ing -> ing.getId().equals("CHC") || ing.getId().equals("CHF"));

        String beganHeader = hasChickenMeat ? "NOT_VEGAN" : "VEGAN";

        return MessageBuilder.withPayload(streetFood)
            .setHeader("DIET_TYPE", beganHeader)
            .build();
    }

    @Bean
    public MessageChannel streetFoodHeaderTransformed() {
        return new DirectChannel();
    }

    @Router(inputChannel = "streetFoodHeaderTransformed")
    public String routeByDietType(Message<StreetFood> message) {
        String dietType = message.getHeaders().get("DIET_TYPE", String.class);

        if ("VEGAN".equals(dietType)) {
            return "streetFoodRoutedVegan";
        } else {
            return "streetFoodRoutedNotVegan";
        }
    }

    @Bean
    public MessageChannel streetFoodRoutedVegan() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel streetFoodRoutedNotVegan() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "streetFoodRoutedVegan")
    public void logVegan(StreetFood streetFood) {
        System.out.println(streetFood + " VEGAN");
    }

    @ServiceActivator(inputChannel = "streetFoodRoutedNotVegan")
    public void logNotVegan(StreetFood streetFood) {
        System.out.println(streetFood + " NOT_VEGAN");
    }

    //Also could use DSL style, it more laconic but less interesting =)
    /*
    EXAMPLE
    @Bean
    public IntegrationFlow streetFoodFlow() {
        return IntegrationFlow.from("streetFoodChannel")
                .split()

                .<StreetFood>filter(this::filterStreetFoodIngredientsMoreThree)

                .<StreetFood>enrichHeaders(headers -> headers.headerFunction("DIET_TYPE", message -> {
                    StreetFood sf = message.getPayload();

                    boolean hasChicken = sf.getIngredient().stream().anyMatch(ing ->
                        "CHC".equals(ing.getCode()) || "CHF".equals(ing.getCode())
                    );

                    return hasChicken ? "NOT_VEGAN" : "VEGAN";
                }))

                .<StreetFood, String>route(
                        message -> message.getHeaders().get("DIET_TYPE", String.class),
                        mapping -> mapping
                                .channelMapping("VEGAN", "streetFoodSplittedVegan")
                                .channelMapping("NOT_VEGAN", "streetFoodSplittedNotVegan")
                )
                .get();
    }

    @Bean
    public IntegrationFlow veganEndpoint() {
        return IntegrationFlow.from("streetFoodSplittedVegan")
                .handle(StreetFood.class, (payload, headers) -> {
                    System.out.println(payload + " VEGAN");
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow notVeganEndpoint() {
        return IntegrationFlow.from("streetFoodSplittedNotVegan")
                .handle(StreetFood.class, (payload, headers) -> {
                    System.out.println(payload + " NOT_VEGAN");
                    return null;
                })
                .get();
    }

     */
}
