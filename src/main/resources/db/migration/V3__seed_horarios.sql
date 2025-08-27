-- Dos horarios próximos; asigna por nombre para no depender de IDs
INSERT INTO core.horarios_clases (clase_id, entrenador_id, fecha, duracion_minutos, capacidad_max)
SELECT c.id, NULL,
       (date_trunc('day', now()) + interval '1 day' + interval '18 hours'),
       60, 12
FROM core.clases c WHERE c.nombre = 'Funcional';

INSERT INTO core.horarios_clases (clase_id, entrenador_id, fecha, duracion_minutos, capacidad_max)
SELECT c.id, NULL,
       (date_trunc('day', now()) + interval '2 day' + interval '10 hours'),
       45, 10
FROM core.clases c WHERE c.nombre = 'EnduOX';
