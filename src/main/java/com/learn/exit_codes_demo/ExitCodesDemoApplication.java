package com.learn.exit_codes_demo;

import com.learn.exit_codes_demo.listner.SampleEventListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

/**
 * When I run the project without any dependency it will stop execution by giving the exit code 0.
 * 
 * The Process finished with exit code 0 simply means that your Spring Boot application started and stopped correctly,
 * but since there were no active components or tasks, it exited immediately. Adding dependencies and components like
 * a web server, CommandLineRunner, or controllers will keep your application running or make it do something useful.
 *
 * Process finished with exit code 0
 *
 * If we want to change this exit code there are 2 methods.
 *
 * Method-1 -> we need to implement ExitCodeGenerator interface
 * Then run the application again. you will see 35 as the exit code
 *
 */
//@SpringBootApplication
//public class ExitCodesDemoApplication implements ExitCodeGenerator {
//
//	public static void main(String[] args) {
//		System.exit(SpringApplication.exit(SpringApplication.run(ExitCodesDemoApplication.class, args)));
//	}
//
//	@Override
//	public int getExitCode() {
//		return 35;
//	}
//}


/**
 * Method-2  Use ExitCodeExceptionMapper class
 */
@SpringBootApplication
public class ExitCodesDemoApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(ExitCodesDemoApplication.class, args)));
	}

	//Lets add the bean of ExitCodeExceptionMapper
	@Bean
	ExitCodeExceptionMapper exitCodeExceptionMapper(){
		return exception -> {
			// Unwrap the actual cause
			Throwable rootCause = getRootCause(exception);
			if (rootCause instanceof NumberFormatException)
				return 34;
			if (rootCause instanceof NullPointerException)
				return 36;
			return 13;
		};
	}

	// Helper method to find the root cause of an exception
	private Throwable getRootCause(Throwable throwable) {
		Throwable cause = throwable;
		while (cause.getCause() != null && cause != cause.getCause()) {
			cause = cause.getCause();
		}
		return cause;
	}


	/**
	 * now lets test this using CommandLineRunner
	 *
	 * In Spring Boot, CommandLineRunner is a functional interface that allows you to run a piece of code at the
	 * startup of the application. It provides a simple way to execute code after the Spring Boot application has been
	 * fully initialized and the Spring Application Context is ready.
	 */
	@Bean
	CommandLineRunner createException(){
		return args -> Integer.parseInt("value");  // Here we are passing string value to convert into int.
		// So it should return NumberFormatException

//		return args -> {
//			String s = null;
//			s.split(" "); // Here we are doing an operation on null. So it should return NullPointerException
//		};
	}

	/**
	 * now lets see we want to catch these exceptions and do some operations based on that.
	 *
	 * For that we need to implement an eventListener. I have done it in a separate class.
	 *
	 * Here we need to create a bean of that class
	 */
	@Bean
	SampleEventListener sampleEventListener(){
		return new SampleEventListener();
	}
}



