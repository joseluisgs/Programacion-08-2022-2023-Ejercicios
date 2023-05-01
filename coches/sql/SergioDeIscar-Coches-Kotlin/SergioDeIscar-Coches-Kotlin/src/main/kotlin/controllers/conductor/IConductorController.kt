package controllers.conductor

import controllers.CrudController
import errors.ConductorError
import models.Conductor
import java.util.UUID

interface IConductorController: CrudController<Conductor, UUID, ConductorError>