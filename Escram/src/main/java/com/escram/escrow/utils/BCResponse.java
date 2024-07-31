package com.escram.escrow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BCResponse {
	private boolean ok;
	private Object message;
}
