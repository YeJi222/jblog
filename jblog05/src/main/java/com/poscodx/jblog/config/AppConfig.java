package com.poscodx.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.jblog.config.app.DBConfig;
import com.poscodx.jblog.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.poscodx.jblog.service", "com.poscodx.jblog.repository"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
}
