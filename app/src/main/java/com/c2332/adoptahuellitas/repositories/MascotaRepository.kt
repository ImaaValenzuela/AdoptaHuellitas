package com.c2332.adoptahuellitas.repositories

import com.c2332.adoptahuellitas.models.Mascota

class MascotaRepository {
    private val mascotas = listOf(
        Mascota(
            id = "1",
            nombre = "Luna",
            especie = "Perro",
            edad = "2 años",
            tamanio = "Mediano",
            genero = "Hembra",
            ubicacion = "Palermo, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Juguetona y cariñosa",
            requisitos = "Necesita espacio para correr",
            fotoUrl = "https://www.quepasapeludo.com/wp-content/uploads/2020/09/IMG-20200825-WA0001-760x410.jpg"
        ),
        Mascota(
            id = "2",
            nombre = "Simba",
            especie = "Gato",
            edad = "3 años",
            tamanio = "Pequeño",
            genero = "Macho",
            ubicacion = "Recoleta, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Tranquilo y relajado",
            requisitos = "Le gustan los lugares cálidos",
            fotoUrl = "https://www.zooplus.es/magazine/wp-content/uploads/2022/02/Gatos-de-exterior.jpeg"
        ),
        Mascota(
            id = "3",
            nombre = "Max",
            especie = "Perro",
            edad = "1 año",
            tamanio = "Grande",
            genero = "Macho",
            ubicacion = "Belgrano, Buenos Aires",
            estadoSalud = "En tratamiento",
            personalidad = "Activo y protector",
            requisitos = "Necesita atención constante",
            fotoUrl = "https://cdn0.expertoanimal.com/es/posts/8/2/6/alimentacion_para_perros_grandes_y_gigantes_25628_600.jpg"
        ),
        Mascota(
            id = "4",
            nombre = "Bella",
            especie = "Conejo",
            edad = "6 meses",
            tamanio = "Pequeño",
            genero = "Hembra",
            ubicacion = "San Telmo, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Curiosa y juguetona",
            requisitos = "Requiere jaula amplia",
            fotoUrl = "https://www.muyinteresante.com/wp-content/uploads/sites/5/2022/10/13/6347b1172c410.jpeg"
        ),
        Mascota(
            id = "5",
            nombre = "Rocky",
            especie = "Perro",
            edad = "4 años",
            tamanio = "Mediano",
            genero = "Macho",
            ubicacion = "Almagro, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Leal y protector",
            requisitos = "Le gusta correr al aire libre",
            fotoUrl = "https://www.rover.com/blog/wp-content/uploads/2019/10/1-2.jpg"
        ),
        Mascota(
            id = "6",
            nombre = "Chester",
            especie = "Gato",
            edad = "2 años",
            tamanio = "Mediano",
            genero = "Macho",
            ubicacion = "Caballito, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Independiente y juguetón",
            requisitos = "Le gusta estar en lugares tranquilos",
            fotoUrl = "https://www.tiendanimal.es/articulos/wp-content/uploads/2016/02/gato-europeo.jpg"
        ),
        Mascota(
            id = "7",
            nombre = "Coco",
            especie = "Perro",
            edad = "6 meses",
            tamanio = "Pequeño",
            genero = "Hembra",
            ubicacion = "Villa Urquiza, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Juguetona y amigable",
            requisitos = "Necesita compañía constante",
            fotoUrl = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=776,fit=contain/article/images/WAMIZ%20ES/terrierescocesnegro.jpg"
        ),
        Mascota(
            id = "8",
            nombre = "Oscar",
            especie = "Gato",
            edad = "5 años",
            tamanio = "Grande",
            genero = "Macho",
            ubicacion = "Flores, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Tranquilo y afectuoso",
            requisitos = "Le gusta la compañía de otros gatos",
            fotoUrl = "https://cdn0.uncomo.com/es/posts/1/7/4/gato_persa_52471_5_600.jpg"
        ),
        Mascota(
            id = "9",
            nombre = "Toby",
            especie = "Perro",
            edad = "3 años",
            tamanio = "Mediano",
            genero = "Macho",
            ubicacion = "Almagro, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Leal y protector",
            requisitos = "Le gusta estar con niños",
            fotoUrl = "https://cdn0.uncomo.com/es/posts/1/7/4/gato_persa_52471_5_600.jpg"
        ),
        Mascota(
            id = "10",
            nombre = "Sasha",
            especie = "Conejo",
            edad = "1 año",
            tamanio = "Pequeño",
            genero = "Hembra",
            ubicacion = "Puebla",
            estadoSalud = "Balvanera, Buenos Aires",
            personalidad = "Curiosa y tranquila",
            requisitos = "Requiere espacio para saltar",
            fotoUrl = "https://hoydia.com.ar/wp-content/uploads/2021/07/conejo_enano_cortado.jpg"
        ),
        Mascota(
            id = "11",
            nombre = "Milo",
            especie = "Perro",
            edad = "2 años",
            tamanio = "Pequeño",
            genero = "Macho",
            ubicacion = "Caballito, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Amistoso y cariñoso",
            requisitos = "Le gusta recibir caricias",
            fotoUrl = "https://hoydia.com.ar/wp-content/uploads/2021/07/conejo_enano_cortado.jpg"
        ),
        Mascota(
            id = "12",
            nombre = "Lola",
            especie = "Gato",
            edad = "4 años",
            tamanio = "Mediano",
            genero = "Hembra",
            ubicacion = "Almagro, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Sociable y activa",
            requisitos = "Le gusta jugar con pelotas",
            fotoUrl = "https://hoydia.com.ar/wp-content/uploads/2021/07/conejo_enano_cortado.jpg"
        ),
        Mascota(
            id = "13",
            nombre = "Benny",
            especie = "Perro",
            edad = "5 años",
            tamanio = "Grande",
            genero = "Macho",
            ubicacion = "San Telmo, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Fiel y protector",
            requisitos = "Requiere ejercicio diario",
            fotoUrl = "https://okdiario.com/img/2021/05/29/san-bernardo-655x368.jpg"
        ),
        Mascota(
            id = "14",
            nombre = "Daisy",
            especie = "Conejo",
            edad = "3 meses",
            tamanio = "Pequeño",
            genero = "Hembra",
            ubicacion = "Palermo, Buenos Aires",
            estadoSalud = "Saludable",
            personalidad = "Activa y curiosa",
            requisitos = "Requiere atención constante",
            fotoUrl = "https://external-preview.redd.it/DAjvrxQ6Pf_zmX2P92zJFkVIemwrKIngW8ChmHQRWWI.jpg?auto=webp&s=b4f9e491f26014a30cc2e779ef488906f8943398"
        )
    )

    fun getMascotas(): List<Mascota> {
        return mascotas
    }
}
