package gaur.himanshu.august.newsapplication

import androidx.room.TypeConverter
import com.google.gson.Gson
import gaur.himanshu.august.newsapplication.retrofit.responce.Source


class RoomTypeConvertor {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun stringToSource(str: String): Source {
        return Gson().fromJson(str, Source::class.java)
    }

}