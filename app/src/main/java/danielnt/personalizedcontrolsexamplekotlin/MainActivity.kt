package danielnt.personalizedcontrolsexamplekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CtlLogin.setOnLoginListener(object : OnLoginListener {
            override fun onLogin(usuario: String, password: String) {
                //Validamos el usuario y la contraseña
                if (usuario.equals("demo") && password.equals("demo"))
                    CtlLogin.setMensaje("Login correcto!")
                else
                    CtlLogin.setMensaje("Vuelva a intentarlo.")
            }
        })

        btnFicha.setOnClickListener(View.OnClickListener { tablero.alternarFichaActiva() })

        tablero.setOnCasillaSeleccionadaListener(object : OnCasillaSeleccionadaListener {
            override fun onCasillaSeleccionada(fila: Int, columna: Int) {
                txtCasilla.text = "Última casilla seleccionada: $fila.$columna"
            }
        })


    }
}
