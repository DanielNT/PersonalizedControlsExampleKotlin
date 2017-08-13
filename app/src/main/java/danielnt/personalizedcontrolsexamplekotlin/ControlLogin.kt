package danielnt.personalizedcontrolsexamplekotlin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.control_login.view.*


/**
 * Created by DanielNT on 13/08/2017.
 */
class ControlLogin: LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?,attrs: AttributeSet) : super(context,attrs)

    private var listener : OnLoginListener? = null

    //doing all the work
    init {
        //Utilizamos el layout 'control_login' como interfaz del control
        val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        li.inflate(R.layout.control_login, this, true)

        //Asociamos los eventos necesarios
        asignarEventos()
    }

    private fun asignarEventos() {
        BtnLogin.setOnClickListener({
            listener?.onLogin(TxtUsuario.text.toString(),
                    TxtPassword.text.toString())
        })
    }

    fun setMensaje(msg: String) {
        LblMensaje.text= msg
    }

    fun setOnLoginListener(l: OnLoginListener) {
        listener = l
    }

}