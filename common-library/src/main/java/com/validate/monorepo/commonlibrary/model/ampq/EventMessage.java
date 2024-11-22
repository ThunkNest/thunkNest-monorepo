package com.validate.monorepo.commonlibrary.model.ampq;

public record EventMessage<T>(
		String messageId,
		T payload,
		long timestamp
) {
	
	@Override
	public String toString() {
		return "RabbitMessage{" +
				"id='" + messageId + '\'' +
				", payload=" + payload +
				", timestamp=" + timestamp +
				'}';
	}
}
