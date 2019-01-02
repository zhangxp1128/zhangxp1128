package org.zhangxuping.util;

import java.util.Base64;

public class EncryptTool {
	public static String encodeBase64(String str) {
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(str.getBytes());
	}
	public static String decodeBase64(String str) {
		Base64.Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(str));
	}
}
