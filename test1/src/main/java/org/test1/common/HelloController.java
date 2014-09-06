package org.test1.common;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.test1.bo.StockBo;
import org.test1.model.Stock;

@Controller
public class HelloController {

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public @ResponseBody Stock welcomePage(@RequestParam(required=false) String stockCode, @RequestParam(required=false) String stockName) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");

		StockBo stockBo = (StockBo) appContext.getBean("stockBo");

		stockCode = org.apache.commons.lang3.StringUtils.defaultString(stockCode, RandomStringUtils.randomNumeric(10));
		stockName = org.apache.commons.lang3.StringUtils.defaultString(stockName, RandomStringUtils.randomAlphanumeric(10));
		
		Stock stock = stockBo.findByStockCode(stockCode);
		if (stock == null){
			stock = new Stock();
			stock.setStockCode(stockCode);
			stock.setStockName(stockName);
			stockBo.save(stock);
		}

		return stock;
	}
	
	@RequestMapping(value = {"/allStock**" }, method = RequestMethod.GET)
	public @ResponseBody List<Stock> allStock() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		
		StockBo stockBo = (StockBo) appContext.getBean("stockBo");
		
		return stockBo.getAllStocks();
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/testnew**", method = RequestMethod.GET)
	public ModelAndView testnew() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("TestNew");

		return model;

	}

}