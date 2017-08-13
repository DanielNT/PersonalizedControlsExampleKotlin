package danielnt.personalizedcontrolsexamplekotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.EditText


/**
* Created by DanielNT on 13/08/2017.
*/
class ExtendedEditText : EditText {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var escala: Float
    private var p1: Paint
    private var p2: Paint

    init {
        p1 = Paint(Paint.ANTI_ALIAS_FLAG)
        p1.color = Color.BLACK
        p1.style = Paint.Style.FILL

        p2 = Paint(Paint.ANTI_ALIAS_FLAG)
        p2.color = Color.WHITE
        p2.textSize = 20F

        escala = resources.displayMetrics.density
    }


    override fun onDraw(canvas: Canvas?) {
        //calling base class method (EditText)
        super.onDraw(canvas)

        //black background
        canvas?.drawRect(width - 30 * escala, 5 * escala, width - 5 * escala, 20 * escala, p1)

        //Text
        canvas?.drawText("" + text.toString().length, width - 28 * escala, 17 * escala, p2)
    }
}