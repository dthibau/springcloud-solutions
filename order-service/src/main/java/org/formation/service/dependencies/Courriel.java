package org.formation.service.dependencies;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Courriel {

	private String to;
	private String subject;
	private String text;
}
