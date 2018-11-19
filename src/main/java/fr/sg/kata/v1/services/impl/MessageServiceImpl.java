package fr.sg.kata.v1.services.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import fr.sg.kata.v1.services.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService{
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public String getMessage(String code) {
		return getMessage(code, null);
	}

	@Override
	public String getMessage(String code, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);	
	}

}
