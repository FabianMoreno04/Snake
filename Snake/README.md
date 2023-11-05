Snake Game
Este proyecto consiste en la creación de una aplicación para el juego de la serpiente basado en los hilos en Java presentados en clase. La aplicación utiliza cuatro hilos para desarrollar el juego, cada uno con una función específica.

Descripción
El juego Snake es un clásico videojuego de estilo arcade en el que el jugador controla una serpiente que se mueve por un tablero o pantalla. El objetivo es comer los alimentos que aparecen en la pantalla, lo que hace que la serpiente crezca. Sin embargo, el jugador debe evitar chocar con la cola de la serpiente o los obstáculos del juego, lo que lo hace más desafiante. El juego continúa hasta que la serpiente choca contra un obstáculo o contra sí misma.

Características
Puntaje
Se ha implementado un hilo para gestionar la puntuación del juego. La puntuación se actualiza con cada alimento consumido por la serpiente. La estrategia utilizada para aumentar la puntuación es la siguiente: se incrementa la puntuación en una unidad cada vez que la serpiente consume un alimento. La puntuación se muestra en la pantalla del juego.

Alimento
Un hilo se encarga de mostrar la comida en la pantalla del juego. La comida aparece y desaparece a intervalos regulares. Si la serpiente la consume, aparece inmediatamente un nuevo alimento en el tablero de juego. Si la serpiente no lo consume antes de que expire el tiempo, aparecerá un nuevo alimento en un lugar diferente. La comida tiene una forma, imagen o figura distinta a las barreras del juego, y cada vez que la serpiente la consume, crece más.

El tiempo del intervalo de comida se define en un archivo de configuración (archivo txt), dependiendo del etiquetado. La estrategia para definir este tiempo se elige según los niveles del juego.

Barreras
Otro hilo se encarga de gestionar los obstáculos del juego. Las barreras aparecen y desaparecen en intervalos de tiempo establecidos. Si la serpiente choca con una barrera, el juego termina y aparece un mensaje de "juego terminado" en la pantalla. Las barreras tienen una forma, imagen o figura distinta a la comida del juego.

El intervalo de tiempo de las barreras se define en un archivo de configuración (archivo txt), dependiendo del etiquetado. La estrategia para definir este tiempo se elige según los niveles del juego. Es importante destacar que el intervalo de tiempo de las barreras debe ser diferente al de la comida.

Movimiento
Se ha creado un hilo que permite que la serpiente se mueva constantemente. Cuando comienza el juego, la serpiente tiene una dirección (derecha, izquierda, arriba, abajo) y se mueve en esa dirección hasta que el jugador presione una tecla para cambiar la dirección.

Las teclas "w" (arriba), "s" (abajo), "d" (izquierda) y "a" (derecha) se utilizan para cambiar la dirección de la serpiente. Si la serpiente choca contra una pared, reaparece en el lado opuesto de la pared. La serpiente comienza con un tamaño inicial según el nivel, y cada vez que consume comida, crece más. A medida que la serpiente crece, su velocidad aumenta según el nivel, y la tasa de aumento se especifica en el archivo de configuración.

Si la serpiente choca con un obstáculo o se topa con ella misma, el juego termina y aparece un mensaje de "juego terminado" en la pantalla, mostrando la puntuación. El nombre de usuario y la puntuación se guardan en un archivo serializado.

El tamaño inicial y la tasa de aumento se definen en un archivo de configuración, dependiendo del etiquetado. La ruta del archivo de configuración y del historial de puntuaciones se debe definir en el archivo README.md del proyecto.

Menú Principal
El menú principal consta de cuatro secciones:

Los usuarios pueden ingresar su nombre.
Hay un botón para iniciar el juego.
Existe un botón para mostrar el historial de puntuaciones.
Se muestra la información del desarrollador, que incluye:
Nombre completo
Identificación del Estudiante
Nombre de la facultad
Nombre de escuela
Año
Curso
Logotipo de la universidad
Historial de Puntuación
Se ha creado una página que muestra una tabla con el historial de puntuaciones. La tabla incluye la fecha y hora del juego, el nombre del jugador y la puntuación. La información de la tabla se lee desde un archivo serializado, y la tabla tiene un scroll si es necesario.



