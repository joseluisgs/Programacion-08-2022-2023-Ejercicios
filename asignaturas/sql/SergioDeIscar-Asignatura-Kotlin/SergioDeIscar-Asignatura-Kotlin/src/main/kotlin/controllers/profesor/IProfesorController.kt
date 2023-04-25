package controllers.profesor

import controllers.CrudController
import errors.ProfesorError
import models.Profesor

interface IProfesorController: CrudController<Profesor, Long, ProfesorError>