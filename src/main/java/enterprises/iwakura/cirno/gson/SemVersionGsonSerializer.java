package enterprises.iwakura.cirno.gson;

import java.io.IOException;

import com.github.zafarkhaja.semver.Version;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SemVersionGsonSerializer {

    /**
     * Registers {@link VersionTypeAdapter} to specified gson builder.
     *
     * @param gsonBuilder GsonBuilder
     *
     * @return specified GsonBuilder
     */
    public static GsonBuilder register(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Version.class, new VersionTypeAdapter());
        return gsonBuilder;
    }

    public static class VersionTypeAdapter extends TypeAdapter<Version> {

        @Override
        public void write(JsonWriter out, Version version) throws IOException {
            if (version == null) {
                out.nullValue();
            } else {
                out.value(version.toString());
            }
        }

        @Override
        public Version read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return Version.tryParse(in.nextString()).orElse(null);
        }
    }
}
