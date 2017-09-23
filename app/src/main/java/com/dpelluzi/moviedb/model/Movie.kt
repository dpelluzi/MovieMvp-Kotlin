package dpelluzi.moviedb.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Movie() : Parcelable {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("poster_path")
    var posterPath: String = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("vote_average")
    var voteAverage: Float = 0F

    @SerializedName("overview")
    var overview: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        posterPath = parcel.readString()
        title = parcel.readString()
        voteAverage = parcel.readFloat()
        overview = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(posterPath)
        parcel.writeString(title)
        parcel.writeFloat(voteAverage)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}