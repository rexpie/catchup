package org.test1.common;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.test1.bo.StockBo;
import org.test1.model.Stock;

@Controller
public class HelloController {

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ApplicationContext appContext = 
	    		new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		
	    	StockBo stockBo = (StockBo)appContext.getBean("stockBo");
	    	
	    	/** insert **/
	    	Stock stock = new Stock();
	    	stock.setStockCode("7668");
	    	stock.setStockName("HAIO");
	    	stockBo.save(stock);
	    	
	    	/** select **/
	    	Stock stock2 = stockBo.findByStockCode("7668");
	    	System.out.println(stock2);
	    	
	    	/** update **/
	    	stock2.setStockName("HAIO-1");
	    	stockBo.update(stock2);
	    	
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", stock2.getStockName());
		model.setViewName("hello");
		return model;

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