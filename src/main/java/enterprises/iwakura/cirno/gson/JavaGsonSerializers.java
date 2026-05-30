package enterprises.iwakura.cirno.gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JavaGsonSerializers {

    /**
     * Registers all basic java.time and other java classes to specified GsonBuilder as type adapters
     *
     * @param gsonBuilder GsonBuilder
     *
     * @return specified GsonBuilder
     */
    public static GsonBuilder register(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantTypeAdapter());
        gsonBuilder.registerTypeAdapter(Period.class, new PeriodTypeAdapter());

        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter());
        gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter());
        gsonBuilder.registerTypeAdapter(ZoneId.class, new ZoneIdTypeAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationTypeAdapter());

        gsonBuilder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());

        // Optional<T> support
        gsonBuilder.registerTypeAdapterFactory(new OptionalTypeAdapterFactory());

        return gsonBuilder;
    }

    public static class InstantTypeAdapter extends TypeAdapter<Instant> {

        @Override
        public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
            if (instant == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(instant.toString()); // ISO-8601 string format
            }
        }

        @Override
        public Instant read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return Instant.parse(jsonReader.nextString());
        }
    }

    public static class PeriodTypeAdapter extends TypeAdapter<Period> {

        @Override
        public void write(JsonWriter jsonWriter, Period period) throws IOException {
            if (period == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(period.toString());
            }
        }

        @Override
        public Period read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return Period.parse(jsonReader.nextString());
        }
    }

    public static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        @Override
        public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
            if (localDate == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localDate.toString()); // ISO_LOCAL_DATE
            }
        }

        @Override
        public LocalDate read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return LocalDate.parse(jsonReader.nextString());
        }
    }

    public static class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            if (localDateTime == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localDateTime.toString()); // ISO_LOCAL_DATE_TIME
            }
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }

    public static class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {

        @Override
        public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {
            if (localTime == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localTime.toString()); // ISO_LOCAL_TIME
            }
        }

        @Override
        public LocalTime read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return LocalTime.parse(jsonReader.nextString());
        }
    }

    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        @Override
        public void write(JsonWriter jsonWriter, OffsetDateTime offsetDateTime) throws IOException {
            if (offsetDateTime == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(offsetDateTime.toString()); // ISO_OFFSET_DATE_TIME
            }
        }

        @Override
        public OffsetDateTime read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return OffsetDateTime.parse(jsonReader.nextString());
        }
    }

    public static class ZonedDateTimeTypeAdapter extends TypeAdapter<ZonedDateTime> {

        @Override
        public void write(JsonWriter jsonWriter, ZonedDateTime zonedDateTime) throws IOException {
            if (zonedDateTime == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(zonedDateTime.toString()); // ISO_ZONED_DATE_TIME
            }
        }

        @Override
        public ZonedDateTime read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return ZonedDateTime.parse(jsonReader.nextString());
        }
    }

    public static class ZoneIdTypeAdapter extends TypeAdapter<ZoneId> {

        @Override
        public void write(JsonWriter jsonWriter, ZoneId zoneId) throws IOException {
            if (zoneId == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(zoneId.getId());
            }
        }

        @Override
        public ZoneId read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return ZoneId.of(jsonReader.nextString());
        }
    }

    public static class DurationTypeAdapter extends TypeAdapter<Duration> {

        @Override
        public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
            if (duration == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(duration.toString()); // ISO-8601 duration format
            }
        }

        @Override
        public Duration read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return Duration.parse(jsonReader.nextString());
        }
    }

    public static class UUIDTypeAdapter extends TypeAdapter<UUID> {

        @Override
        public void write(JsonWriter jsonWriter, UUID uuid) throws IOException {
            if (uuid == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(uuid.toString());
            }
        }

        @Override
        public UUID read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return UUID.fromString(jsonReader.nextString());
        }
    }

    /**
     * Generic {@link Optional} TypeAdapterFactory that delegates to Gson for the contained type.
     * Serializes absent Optionals as JSON null, present Optionals by delegating to the contained adapter.
     */
    public static class OptionalTypeAdapterFactory implements TypeAdapterFactory {

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Type type = typeToken.getType();
            Class<? super T> rawType = typeToken.getRawType();

            if (!Optional.class.isAssignableFrom(rawType)) {
                return null;
            }

            Type containedType;
            if (type instanceof ParameterizedType) {
                containedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                containedType = Object.class;
            }

            TypeAdapter<Object> valueAdapter = (TypeAdapter) gson.getAdapter(TypeToken.get(containedType));

            TypeAdapter<Optional<?>> optionalAdapter = new TypeAdapter<Optional<?>>() {
                @Override
                public void write(JsonWriter out, Optional<?> value) throws IOException {
                    //noinspection OptionalAssignedToNull
                    if (value == null) {
                        out.nullValue();
                        return;
                    }
                    Object inner = value.orElse(null);
                    // delegate to the inner adapter (it will handle nulls)
                    valueAdapter.write(out, inner);
                }

                @Override
                public Optional<?> read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return Optional.empty();
                    }
                    Object obj = valueAdapter.read(in);
                    return Optional.ofNullable(obj);
                }
            };

            return (TypeAdapter<T>) optionalAdapter;
        }
    }
}