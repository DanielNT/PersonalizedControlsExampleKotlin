package danielnt.personalizedcontrolsexamplekotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent


/**
 * Created by DanielNT on 13/08/2017.
 */
class TresEnRaya : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        addXMLAtrributes(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        addXMLAtrributes(attrs)
    }

    val VACIA = 0
    val FICHA_O = 1
    val FICHA_X = 2

    private var tablero: Array<IntArray>? = null
    private var listener: OnCasillaSeleccionadaListener? = null
    var fichaActiva: Int = 0
    var xColor: Int = 0
    var oColor: Int = 0


    init {
        tablero = Array(3) { IntArray(3) }
        limpiar()

        fichaActiva = FICHA_X
        xColor = Color.RED
        oColor = Color.BLUE
    }

    //default size 100x100, will use all space available
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var ancho = calcularAncho(widthMeasureSpec)
        var alto = calcularAlto(heightMeasureSpec)

        if (ancho < alto)
            alto = ancho
        else
            ancho = alto

        setMeasuredDimension(ancho, alto)
    }

    private fun calcularAlto(limitesSpec: Int): Int {
        var res = 100 //Alto por defecto

        val modo = View.MeasureSpec.getMode(limitesSpec)
        val limite = View.MeasureSpec.getSize(limitesSpec)

        if (modo == View.MeasureSpec.AT_MOST) {
            res = limite
        } else if (modo == View.MeasureSpec.EXACTLY) {
            res = limite
        }

        return res
    }

    private fun calcularAncho(limitesSpec: Int): Int {
        var res = 100 //Ancho por defecto

        val modo = View.MeasureSpec.getMode(limitesSpec)
        val limite = View.MeasureSpec.getSize(limitesSpec)

        if (modo == View.MeasureSpec.AT_MOST) {
            res = limite
        } else if (modo == View.MeasureSpec.EXACTLY) {
            res = limite
        }

        return res
    }

    private fun limpiar() {
        for (i in 0..2)
            for (j in 0..2)
                tablero?.get(i)?.set(j, VACIA)
    }

    fun setCasilla(fil: Int, col: Int, valor: Int) {
        tablero!![fil][col] = valor
    }

    fun getCasilla(fil: Int, col: Int): Int {
        return tablero!![fil][col]
    }

    fun alternarFichaActiva() {
        fichaActiva = if (fichaActiva == FICHA_O) FICHA_X else FICHA_O
    }

    override fun onDraw(canvas: Canvas) {
        //Obtenemos las dimensiones del control
        val alto = measuredHeight.toFloat()
        val ancho = measuredWidth.toFloat()

        //Lineas
        val pBorde = Paint()
        pBorde.style = Paint.Style.STROKE
        pBorde.color = Color.BLACK
        pBorde.strokeWidth = 2f

        canvas.drawLine(ancho / 3f, 0f, ancho / 3f, alto, pBorde)
        canvas.drawLine(2 * ancho / 3f, 0f, 2 * ancho / 3f, alto, pBorde)

        canvas.drawLine(0f, alto / 3, ancho, alto / 3, pBorde)
        canvas.drawLine(0f, 2 * alto / 3, ancho, 2 * alto / 3, pBorde)

        //Marco
        canvas.drawRect(0f, 0f, ancho, alto, pBorde)

        //Marcas
        val pMarcaO = Paint()
        pMarcaO.style = Paint.Style.STROKE
        pMarcaO.strokeWidth = 8f
        pMarcaO.color = oColor

        val pMarcaX = Paint()
        pMarcaX.style = Paint.Style.STROKE
        pMarcaX.strokeWidth = 8f
        pMarcaX.color = xColor

        //Casillas Seleccionadas
        for (fil in 0..2) {
            for (col in 0..2) {

                if (tablero!![fil][col] == FICHA_X) {
                    //Cruz
                    canvas.drawLine(
                            col * (ancho / 3) + ancho / 3 * 0.1f,
                            fil * (alto / 3) + alto / 3 * 0.1f,
                            col * (ancho / 3) + ancho / 3 * 0.9f,
                            fil * (alto / 3) + alto / 3 * 0.9f,
                            pMarcaX)

                    canvas.drawLine(
                            col * (ancho / 3) + ancho / 3 * 0.1f,
                            fil * (alto / 3) + alto / 3 * 0.9f,
                            col * (ancho / 3) + ancho / 3 * 0.9f,
                            fil * (alto / 3) + alto / 3 * 0.1f,
                            pMarcaX)
                } else if (tablero!![fil][col] == FICHA_O) {
                    //Circulo
                    canvas.drawCircle(
                            (col * (ancho / 3) + ancho / 6f),
                            fil * (alto / 3) + alto / 6f,
                            ancho / 6 * 0.8f, pMarcaO)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val fil = (event.y / (measuredHeight / 3)).toInt()
        val col = (event.x / (measuredWidth / 3)).toInt()

        tablero!![fil][col] = fichaActiva

        //Lanzamos el evento de pulsación
        if (listener != null) {
            listener!!.onCasillaSeleccionada(fil, col)
        }

        //Refrescamos el control
        this.invalidate()

        return super.onTouchEvent(event)
    }

    fun setOnCasillaSeleccionadaListener(l: OnCasillaSeleccionadaListener) {
        listener = l
    }

    fun addXMLAtrributes(attrs: AttributeSet?) {
        // Procesamos los atributos XML personalizados
        val a = context.obtainStyledAttributes(attrs,
                R.styleable.TresEnRaya)

        oColor = a.getColor(
                R.styleable.TresEnRaya_ocolor, Color.BLUE)

        xColor = a.getColor(
                R.styleable.TresEnRaya_xcolor, Color.RED)

        a.recycle()
    }
}