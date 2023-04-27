package repositories.base

import models.LineaBurguer


interface IRepositoryCrudToLineas<Model>: IRepositoryCrud<Model> {
    fun getAllLineaBurguer(): List<LineaBurguer>
}