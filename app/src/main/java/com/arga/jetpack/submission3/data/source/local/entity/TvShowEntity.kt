package com.arga.jetpack.submission3.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshow")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "posterpath")
    @SerializedName("poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "backdroppath")
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name = "firstairdate")
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String?,
    @ColumnInfo(name = "voteaverage")
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @ColumnInfo(name = "isfavorite")
    var isFavorite: Boolean = false
)