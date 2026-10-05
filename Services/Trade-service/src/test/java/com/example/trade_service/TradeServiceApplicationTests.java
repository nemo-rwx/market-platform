package com.example.trade_service;

import com.example.trade_service.domain.Trade;
import com.example.trade_service.domain.TradeStatus;
import com.example.trade_service.dto.TradeResponse;
import com.example.trade_service.Exceptions.InvalidTradeException;
import com.example.trade_service.Exceptions.TradeNotFoundException;
import com.example.trade_service.event.TradeEventPublisher;
import com.example.trade_service.repository.TradeRepository;
import com.example.trade_service.service.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;

	@Mock
	private TradeEventPublisher tradeEventPublisher;

	@InjectMocks
	private TradeService tradeService;


	@Test
	void shouldExecuteNewTrade() {

		Trade trade = new Trade();
		trade.setId(1L);
		trade.setStatus(TradeStatus.NEW);

		when(tradeRepository.findById(1L))
				.thenReturn(Optional.of(trade));

		when(tradeRepository.save(trade))
				.thenReturn(trade);

		TradeResponse response = tradeService.executeTrade(1L);

		assertEquals(TradeStatus.EXECUTED, response.status());
	}


	@Test
	void shouldNotExecuteAlreadyExecutedTrade() {

		Trade trade = new Trade();
		trade.setId(1L);
		trade.setStatus(TradeStatus.EXECUTED);

		when(tradeRepository.findById(1L))
				.thenReturn(Optional.of(trade));

		assertThrows(
				InvalidTradeException.class,
				() -> tradeService.executeTrade(1L)
		);
	}


	@Test
	void shouldCancelNewTrade() {

		Trade trade = new Trade();
		trade.setId(1L);
		trade.setStatus(TradeStatus.NEW);

		when(tradeRepository.findById(1L))
				.thenReturn(Optional.of(trade));

		when(tradeRepository.save(trade))
				.thenReturn(trade);

		TradeResponse response = tradeService.cancelTrade(1L);

		assertEquals(TradeStatus.CANCELLED, response.status());
	}


	@Test
	void shouldNotCancelExecutedTrade() {

		Trade trade = new Trade();
		trade.setId(1L);
		trade.setStatus(TradeStatus.EXECUTED);

		when(tradeRepository.findById(1L))
				.thenReturn(Optional.of(trade));

		assertThrows(
				InvalidTradeException.class,
				() -> tradeService.cancelTrade(1L)
		);
	}


	@Test
	void shouldThrowExceptionWhenTradeDoesNotExist() {

		when(tradeRepository.findById(999L))
				.thenReturn(Optional.empty());

		assertThrows(
				TradeNotFoundException.class,
				() -> tradeService.executeTrade(999L)
		);
	}
}