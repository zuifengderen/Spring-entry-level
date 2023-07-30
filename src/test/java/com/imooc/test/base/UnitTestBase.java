package com.imooc.test.base;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class UnitTestBase {
	
	private ClassPathXmlApplicationContext context;
	
	private String springXmlpath;
	
	public UnitTestBase() {}
	
	public UnitTestBase(String springXmlpath) {
		this.springXmlpath = springXmlpath;
	}

	/**
	 * 在测试方法执行前执行该方法。
	 * 加载配置文件，并创建Spring容器和启动。
	 */
	@Before
	public void before() {
		if (StringUtils.isEmpty(springXmlpath)) {
			springXmlpath = "classpath*:spring-*.xml";
		}
		try {
			context = new ClassPathXmlApplicationContext(springXmlpath.split("[,\\s]+"));
			context.start();
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在测试方法执行后执行该方法。
	 * 销毁Spring容器，释放资源。
	 */
	@After
	public void after() {
		context.destroy();
	}

	/**
	 * 获取Spring容器中指定名称的Bean
	 * ps:注解@SuppressWarnings("unchecked")的作用是告诉编译器忽略未经检查的转换类型的警告信息
	 * 这是因为Java中的泛型是在编译时进行检查的，而context.getBean()方法返回的是Object类型，所以编译器会提示未经检查的转换类型告警。
	 * @param beanId
	 * @return
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getBean(String beanId) {
		try {
			return (T)context.getBean(beanId);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取Spring容器中指定类型的Bean
	 * @param clazz
	 * @return
	 * @param <T>
	 */
	protected <T extends Object> T getBean(Class<T> clazz) {
		try {
			return context.getBean(clazz);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}

}
