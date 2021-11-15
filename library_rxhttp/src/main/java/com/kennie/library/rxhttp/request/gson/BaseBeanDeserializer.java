package com.kennie.library.rxhttp.request.gson;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.kennie.library.rxhttp.request.base.BaseBean;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * 描述：
 *
 */
public class BaseBeanDeserializer implements JsonDeserializer<BaseBean> {
    @Nullable
    @Override
    public BaseBean deserialize(@NonNull JsonElement json, @NonNull Type typeOfT, @NonNull JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject jsonObj = json.getAsJsonObject();
        try {
            Class<?> clazz = (Class<?>) typeOfT;
            BaseBean baseBean = (BaseBean) clazz.newInstance();
            for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
                String itemKey = entry.getKey();
                JsonElement itemElement = entry.getValue();
                try {
                    Field field = clazz.getDeclaredField(itemKey);
                    field.setAccessible(true);
                    Object item = context.deserialize(itemElement, field.getType());
                    field.set(baseBean, item);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return baseBean;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
