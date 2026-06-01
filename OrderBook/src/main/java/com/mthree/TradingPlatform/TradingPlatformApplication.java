package com.mthree.TradingPlatform;

import com.mthree.TradingPlatform.domain.model.Order;
import com.mthree.TradingPlatform.domain.model.OrderSide;
import com.mthree.TradingPlatform.domain.model.Trade;
import com.mthree.TradingPlatform.service.OrderBookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class TradingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingPlatformApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(OrderBookService orderBookService) {
		return args -> {

			String symbol = "AAPL";

			// BUY orders
			Order buy1 = new Order(UUID.randomUUID(), 10, new BigDecimal("100.00"), OrderSide.BUY);
			Order buy2 = new Order(UUID.randomUUID(), 5, new BigDecimal("101.00"), OrderSide.BUY);

			// SELL order (once placed will trigger match)
			Order sell1 = new Order(UUID.randomUUID(), 12, new BigDecimal("100.00"), OrderSide.SELL);

			System.out.println("Placing BUY orders...");
			orderBookService.placeOrder(symbol, buy1);
			orderBookService.placeOrder(symbol, buy2);

			System.out.println("Placing SELL order (should match)...");
			List<Trade> trades = orderBookService.placeOrder(symbol, sell1);

			System.out.println("TRADES:");
			trades.forEach(System.out::println);

			System.out.println("Best Bid: " + orderBookService.getHighestBid(symbol));
			System.out.println("Best Ask: " + orderBookService.getLowestAsk(symbol));
		};
	}

}
