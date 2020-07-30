package com.firatkaya.converters;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

@Component
public class UsernameConverters implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		System.out.println("Şifrelendi. Db ye giden data :"+attribute);
		return null;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		System.out.println("Şifre çözüldü. Db den gelen data :"+dbData);
		return null;
	}

}
