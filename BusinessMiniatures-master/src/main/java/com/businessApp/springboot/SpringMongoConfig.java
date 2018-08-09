package com.businessApp.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig
{

	@Value("${spring.data.mongodb.host}")
	private String mongoHost;

	@Value("${spring.data.mongodb.database}")
	private String database1;

	@Value("${spring.data.mongodb.port}")
	private String mongoPort;

	// @Value("${spring.data.mongodb.port}")
	private String folderPath;

	@Autowired
	private MongoDbFactory mongoDbFactory;

	@Bean
	public MongoDbFactory mongoDBFactory() throws Exception
	{
		System.out.println("*Host isss*" + this.mongoHost + ",port is "
				+ this.mongoPort + " db value is " + this.database1);
		return new SimpleMongoDbFactory(new MongoClient(this.mongoHost),
				this.database1);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception
	{

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(
				this.mongoDbFactory);

		// Remove _class
		MappingMongoConverter converter = new MappingMongoConverter(
				dbRefResolver, new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return new MongoTemplate(mongoDBFactory(), converter);

	}

}
