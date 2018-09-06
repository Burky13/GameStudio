package sk.tsystems.gamestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.tsystems.gamestudio.service.ScoreService.ScoreService;
import sk.tsystems.gamestudio.service.ScoreService.ScoreServiceJPA;
import sk.tsystems.gamestudio.service.commentService.CommentService;
import sk.tsystems.gamestudio.service.commentService.CommentServiceJPA;
import sk.tsystems.gamestudio.service.ratingService.RatingService;
import sk.tsystems.gamestudio.service.ratingService.RatingServiceJPA;
import sk.tsystems.gamestudio.service.userService.UserService;
import sk.tsystems.gamestudio.service.userService.UserServiceJPA;

@SpringBootApplication
@Configuration
public class SpringMain {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args); 
	}
	
//	@Bean
//	public CommandLineRunner runner(MainConsole mainConsole) {
////	Full anonymous implementation of CommandLineRunner	
////		return new CommandLineRunner() {
////			@Override
////			public void run(String... arg0) throws Exception {
////				mainConsole.run();
////			}
////		};
//		
////	Implementation using lambda with typed parameter 
////		return (String... args0) -> {
////			mainConsole.run();
////		};
//		
////	Implementation with clean lambda
//		return (args0) -> mainConsole.run();
//	}
	
//	@Bean
//	public MainConsole xy() {
//		return new MainConsole();
//	}
	
	@Bean
	public ScoreService scoreService() {
//		return new ScoreServiceJDBC();
//		return new ScoreServiceFile();
		return new ScoreServiceJPA();
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceJPA();
	}
	
	@Bean
	public CommentService commentService() {
		return new CommentServiceJPA();
	}
	
	@Bean
	public RatingService ratingService() {
		return new RatingServiceJPA();
	}
	
	
//	@Bean
//	public MinesConsoleUI mineConsoleUI() {
//		return new MinesConsoleUI();
//	}
//	
//	@Bean
//	public PuzzleConsoleUI puzzleConsoleUI() {
//		return new PuzzleConsoleUI();
//	}
//	
//	@Bean
//	public GuessANumberConsoleUI guessNumberConsoleUI() {
//		return new GuessANumberConsoleUI(100);
//	}
}
