package com.chitrakoot.parking.utillity;

import lombok.Data;

@Data
public class MailEntity {
	private String toMail;
	private String fromMail;
	private String subjectMail;
	private String messageBody;

}
