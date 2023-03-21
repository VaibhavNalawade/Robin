import com.google.firebase.Timestamp
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

class TimestampTypeAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {

    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.let { Timestamp(it).seconds })
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
        return json?.let {
            val timestamp = Timestamp(it.asLong, 0)
            timestamp.toDate()
        } ?: Date()
    }
}
