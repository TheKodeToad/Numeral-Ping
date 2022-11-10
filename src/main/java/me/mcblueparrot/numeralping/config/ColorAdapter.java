package me.mcblueparrot.numeralping.config;

import java.awt.Color;
import java.lang.reflect.Type;

import com.google.gson.*;

final class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

	public static final ColorAdapter INSTANCE = new ColorAdapter();

	@Override
	public Color deserialize(JsonElement paramJsonElement, Type paramType,
			JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
		return new Color(paramJsonElement.getAsInt() /* remove alpha for now */);
	}

	@Override
	public JsonElement serialize(Color paramT, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
		return new JsonPrimitive(paramT.getRGB());
	}

}
