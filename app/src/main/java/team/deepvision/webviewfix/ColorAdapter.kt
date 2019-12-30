package team.deepvision.webviewfix

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_sschool_selection_color.view.*
import team.deepvision.webviewfix.data.SSchoolHighlight

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.Vh>() {

    var data: List<SSchoolHighlight.HighlightingColor> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedColorValue: String = ""
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: ((color: SSchoolHighlight.HighlightingColor) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Vh(parent)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(data[position], data[position].argb == selectedColorValue)
    }

    inner class Vh(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_sschool_selection_color,
            parent,
            false
        )
    ) {

        val bgColor: ImageView = itemView.sschool_selection_color_fill_iv
        val tick: ImageView = itemView.sschool_selection_color_tick_iv

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.invoke(data[adapterPosition])
                }
            }
        }

        fun bind(color: SSchoolHighlight.HighlightingColor, isSelected: Boolean) {
            if (color.argb == SSchoolHighlight.HighlightingColor.NOT_SELECTED.argb) bgColor.apply {
                visibility = View.GONE
            } else bgColor.apply {
                visibility = View.VISIBLE
                bgColor.setColorFilter(Color.parseColor(color.argb))
            }
            tick.visibility = if (isSelected) View.VISIBLE else View.GONE
        }

    }

}
