package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.OrderFilter
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.domain.entity.Type

@Entity(
    tableName = "filter",
    foreignKeys = [
        ForeignKey(
            entity = ItemDb::class,
            parentColumns = ["id", "type"],
            childColumns = ["item_id","item_type"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class FilterDb(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("year_bottom")val yearBottom: Int,
    @ColumnInfo("year_top")val yearTop: Int,
    @ColumnInfo("rating_bottom")val ratingBottom: Int,
    @ColumnInfo("rating_top")val ratingTop: Int,
    val order: Int,
    val type: Int,
    @ColumnInfo("item_id")val itemId: Int?,
    @ColumnInfo("item_type")val itemType: Int?
){

    fun toSearchFilter(genres:List<Int>,countries:List<Int>):SearchFilter{
        return SearchFilter(
            yearBottom = this.yearBottom,
            yearTop = this.yearTop,
            ratingBottom = this.ratingBottom,
            ratingTop = this.ratingTop,
            order = OrderFilter.values()[this.order],
            type= Type.values()[this.type],
            genres= genres,
            countries= countries


        )

    }
    companion object{
        fun fromSearchFilter(searchFilter: SearchFilter,itemId: Int?, itemType: Int?): FilterDb{
            return FilterDb(
                id = 0,
                yearBottom = searchFilter.yearBottom,
                yearTop = searchFilter.yearTop ,
                ratingBottom = searchFilter.ratingBottom,
                ratingTop = searchFilter.ratingTop,
                order = searchFilter.order.ordinal,
                type = searchFilter.type.ordinal,
                itemId = itemId,
                itemType = itemType
            )
        }
    }
}