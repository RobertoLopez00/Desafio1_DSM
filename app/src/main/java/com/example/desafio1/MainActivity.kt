package com.example.desafio1 // Asegúrate de que este es tu paquete correcto

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var operationSpinner: Spinner
    private lateinit var firstNumberEditText: EditText
    private lateinit var secondNumberEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar las vistas
        operationSpinner = findViewById(R.id.operationSpinner)
        firstNumberEditText = findViewById(R.id.firstNumberEditText)
        secondNumberEditText = findViewById(R.id.secondNumberEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Obtener el array de operaciones desde strings.xml
        val operations = resources.getStringArray(R.array.Operaciones)

        // Crear el ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignando el adapter al Spinner
        operationSpinner.adapter = adapter

        // Manejar la selección de un elemento
        operationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedOperation= parent.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity, "Operación seleccionada: $selectedOperation", Toast.LENGTH_SHORT).show()
                //Toast para mostrar la operación seleccionada en una notificaciónxd
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se seleccionó nada
            }
        }

        // Manejar el clic del botón "Calcular"
        calculateButton.setOnClickListener {
            calculateResult()
        }
    }
    private fun calculateResult() {
        //Obtener los números ingresados
        val firstNumber: Double? = firstNumberEditText.text.toString().toDoubleOrNull()
        val secondNumber: Double? = secondNumberEditText.text.toString().toDoubleOrNull()

        //Variable para indicar si hay un error
        var hasError = false

        //Validar que se hayan ingresado números
        if (firstNumber == null || secondNumber == null) {
            resultTextView.text = "Error: Ingrese números válidos"
            //Limpiar los EditText
            firstNumberEditText.text.clear()
            secondNumberEditText.text.clear()
            hasError = true //Indica que hay un error
        }

        //Si hay un error, no continuar con el cálculo
        if (hasError) {
            return
        }

        //Obtener la operación seleccionada
        val selectedOperation = operationSpinner.selectedItem.toString()

        //Variable para guardar el resultado o el mensaje de error
        var result: Any = 0.0 //Inicializamos con un valor por defecto

        // Realizar la operación
        when (selectedOperation) {
            "Suma" -> result = firstNumber!! + secondNumber!! //!! es una forma de decirle a Kotlin que confías en que una variable no es nula.
            "Resta" -> result = firstNumber!! - secondNumber!! //Significa que si falla, tirará una excepcion NullPointerException, no deberia de pasar pero es bueno saberlo xd
            "Multiplicacion" -> result = firstNumber!! * secondNumber!!
            "Division" -> {
                if (secondNumber == 0.0) {
                    result = "Error: No se puede dividir por cero"
                } else {
                    result = firstNumber!! / secondNumber!!
                }
            }
            else -> result = 0.0 // Valor por defecto si no se selecciona ninguna operación
        }

        if (result is String) {
            resultTextView.text = result
        } else {
            resultTextView.text = "Resultado: ${result.toString()}"
        }
    }
}