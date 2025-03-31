//package com.example.masanz.aimar.actividades.model.service;
//
//import com.example.masanz.aimar.actividades.model.DAO.StorageDAO;
//import com.example.masanz.aimar.actividades.model.Util.ImageUtils;
//import com.example.masanz.aimar.actividades.model.entity.Imagen;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.spi.IIOServiceProvider;
//import java.io.IOException;
//
//@Service
//public class StorageService {
//
//    @Autowired
//    private StorageDAO storageDAO;
//
//    public String guardarFoto(MultipartFile foto) throws IOException {
//        byte[] compressedImage = ImageUtils.compressImage(foto.getBytes());
//
//        // Usar el constructor sin @Builder
//        Imagen imagen = new Imagen(compressedImage);  // id será null porque es autogenerado
//
//        storageDAO.save(imagen);
//
//        return "Foto guardada correctamente";
//    }
//
//    public byte[] descargarFoto(Long id) {
//        // Obtienes la imagen directamente por su ID
//        Imagen imagen = storageDAO.getReferenceById(id);
//
//        // Recuperas los datos de la imagen almacenados en 'foto'
//        byte[] foto = imagen.getFoto();  // Accede a 'foto' directamente
//
//        // Si tienes un método para descomprimir (si es necesario), puedes llamarlo aquí
//        return ImageUtils.decompressImage(foto);
//    }
//}
