package com.firatkaya.model.excep;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.rest.core.config.Projection;

import com.firatkaya.entity.Post;

import java.io.Serializable;

@Projection(name = "excerptSearch", types = Post.class)
public interface PostExceptrSearch {
	
	String getPost_Id();
	
	String getPost_Tag();
	
	String getPost_Title();
	
	String getPost_Header();
	
	
}
