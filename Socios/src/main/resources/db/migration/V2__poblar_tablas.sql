INSERT INTO Membresias (tipo, precio, duracion_meses) VALUES
                                                          ('Mensual',     28000, 1),
                                                          ('Trimestral',  25000, 3),
                                                          ('Semestral',   22500, 6),
                                                          ('Anual',       20000, 12);

-- rutinaId apunta a las rutinas ya existentes en rutina_db (1=Full Body, 2=Piernas, etc.)
INSERT INTO Socios (rut, nombre, email, estado_membresia, plan_id, rutina_id) VALUES
                                                                                  ('22665456-9', 'Roberto Medina',  'rob.medina@gmail.com',  TRUE, 1, 1),
                                                                                  ('21802504-8', 'Julia Gonzalez',  'jul.gonzalez@gmail.com', TRUE, 3, 2),
                                                                                  ('20503580-k', 'David Ibanez',    'dav.ibanez@gmail.com',  TRUE, 2, 3);