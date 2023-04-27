package factory.motor

import models.motor.*

object MotoresFactory {

    fun getMotorRandom(): Motor{
        val chance = (1..100).random()
        val modelos = listOf<String>("Peugeot", "Toyota", "Citroen", "Alfa Romeo", "Renault")
        val caballos = (75..250).random()
        return if(chance <= 25){
            MotorGasolina(
                modelo = modelos.random(),
                caballos = caballos,
                cilindradaGasolina = (2..8).random()
            )
        }else{
            if(chance <= 50){
                MotorDiesel(
                    modelo = modelos.random(),
                    caballos = caballos,
                    cilindradaDiesel = (2..8).random()
                )
            }else{
                if(chance <= 75){
                    MotorHibrido(
                        modelo = modelos.random(),
                        caballos = caballos,
                        capacidadElectrica = ((1..250).random()*100.0)/100.0,
                        capacidadGasolina = ((1..250).random()*100.0)/100.0
                    )
                }else{
                    MotorElectrico(
                        modelo = modelos.random(),
                        caballos = caballos,
                        porcentajeCargado = (Math.random()*100.0)
                    )
                }
            }
        }
    }
}