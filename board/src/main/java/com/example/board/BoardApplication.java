package com.example.board;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.example.board.mapper"})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
		
		List<String> array = new ArrayList<>();
		array.add(0,"a");
		System.out.println(array.get(0)); 
		
	}

}
