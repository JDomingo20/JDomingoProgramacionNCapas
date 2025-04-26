package com.JDomingoProgramacionNCapas.Controller;

import com.JDomingoProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.JDomingoProgramacionNCapas.DAO.DireccionDAOImplementation;
import com.JDomingoProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.JDomingoProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.JDomingoProgramacionNCapas.DAO.PaisDAOImplementation;
import com.JDomingoProgramacionNCapas.DAO.RolDAOImplementation;
import com.JDomingoProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.JDomingoProgramacionNCapas.ML.Colonia;
import com.JDomingoProgramacionNCapas.ML.Direccion;
import com.JDomingoProgramacionNCapas.ML.Estado;
import com.JDomingoProgramacionNCapas.ML.Municipio;
import com.JDomingoProgramacionNCapas.ML.Pais;
import com.JDomingoProgramacionNCapas.ML.Result;
import com.JDomingoProgramacionNCapas.ML.ResultFile;
import com.JDomingoProgramacionNCapas.ML.Rol;
import com.JDomingoProgramacionNCapas.ML.Usuario;
import com.JDomingoProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private RolDAOImplementation rolDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping("/CargaMasiva")
    public String CargaMasiva() {
        System.out.println("");
        return "CargaMasiva";
    }

    @GetMapping("/Procesar")
    public String ProcesarArchivo(HttpSession session) {
        String absolutePath = session.getAttribute("urlFile").toString();
        
        List<UsuarioDireccion> listaUsuarios = LecturaArchivoTXT(new File(absolutePath));
        
        for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
            usuarioDAOImplementation.Add(usuarioDireccion);
        }
        
        session.removeAttribute("urlFile");
        
        System.out.println("");
        return "redirect:/Usuario/CargaMasiva";
    }

    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
        try {
            if (archivo != null && !archivo.isEmpty()) {
                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];

                String root = System.getProperty("user.dir");
                String path = "src/main/resources/static/Archivos";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmSS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
                archivo.transferTo(new File(absolutePath));

                //Leer archivo
                List<UsuarioDireccion> listaUsuarios = new ArrayList<>();

                if (tipoArchivo.equals("txt")) {
                    listaUsuarios = LecturaArchivoTXT(new File(absolutePath));

                } else {
                    listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
                }

                //Validar archivo
                List<ResultFile> listaErrores = ValidarArchivo(listaUsuarios);
                if (listaErrores.isEmpty()) {
                    //Procesar archivo
                    session.setAttribute("urlFile", absolutePath);
                    model.addAttribute("listaErrores", listaErrores);

                } else {
                    //Enviar errores
                    model.addAttribute("listaErrores", listaErrores);

                }
            }

        } catch (Exception ex) {
            return "redirect:/Alumno/CargaMasiva";

        }
        return "CargaMasiva";

    }

    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
        List<UsuarioDireccion> ListaUsuarios = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {

                for (Row row : sheet) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
                    usuarioDireccion.Usuario.setNumeroTelefonico(row.getCell(3).toString());
                    usuarioDireccion.Usuario.setCorreoElectronico(row.getCell(4).toString());
                    //Formato a fecha de nacimiento
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//Aqui se da formato
                    usuarioDireccion.Usuario.setFechaNacimiento(formatter.parse(formatter.format(row.getCell(5).getDateCellValue())));
                    usuarioDireccion.Usuario.setUserName(row.getCell(6).toString());
                    usuarioDireccion.Usuario.setPassword(row.getCell(7).toString());
                    usuarioDireccion.Usuario.setSexo(row.getCell(8).toString());
                    usuarioDireccion.Usuario.setCURP(row.getCell(9).toString());
                    usuarioDireccion.Usuario.setCelular(row.getCell(10).toString());
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol((int) row.getCell(11).getNumericCellValue());
                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(12).toString());
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(13).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14).toString());
                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(15).getNumericCellValue());
                    ListaUsuarios.add(usuarioDireccion);
                }
            }

        } catch (Exception ex) {
            System.out.println("Error al abrir archivo");
        }
        return ListaUsuarios;
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        List<UsuarioDireccion> listaUusarios = new ArrayList();

        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferReader = new BufferedReader(fileReader);) {

            String linea;
            while ((linea = bufferReader.readLine()) != null) {
                String[] campos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombre(campos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
                usuarioDireccion.Usuario.setNumeroTelefonico(campos[3]);
                usuarioDireccion.Usuario.setCorreoElectronico(campos[4]);
                //Formato a fecha de nacimiento
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//Aqui se da formato
                usuarioDireccion.Usuario.setFechaNacimiento(formatter.parse(campos[5]));
                usuarioDireccion.Usuario.setUserName(campos[6]);
                usuarioDireccion.Usuario.setPassword(campos[7]);
                usuarioDireccion.Usuario.setSexo(campos[8]);
                usuarioDireccion.Usuario.setCURP(campos[9]);
                usuarioDireccion.Usuario.setCelular(campos[10]);
                usuarioDireccion.Usuario.Rol = new Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(Integer.parseInt(campos[11]));
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(campos[12]);
                usuarioDireccion.Direccion.setNumeroExterior(campos[13]);
                usuarioDireccion.Direccion.setNumeroInterior(campos[14]);
                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[15]));

                listaUusarios.add(usuarioDireccion);

            }

        } catch (Exception ex) {
            listaUusarios = null;
        }

        return listaUusarios;
    }

    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {
        List<ResultFile> listaErrores = new ArrayList<>();
        if (listaUsuarios == null) {
            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
        } else if (listaUsuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "La lista es vacia", "La lista es vacia"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getNombre(), "El nombr es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "El apellido es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getUserName() == null || usuarioDireccion.Usuario.getUserName().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getUserName(), "El UserName es un campo obligatorio"));
                }
            }
        }

        return listaErrores;
    }

    @GetMapping
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();
        Result resultJPA =usuarioDAOImplementation.GetAllJPA();
        Result resultRol = rolDAOImplementation.GetAll();
        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.Rol = new Rol();

        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
        model.addAttribute("roles", resultRol.object);
        model.addAttribute("listaUsuario", resultJPA.objects);

        return "UsuarioIndex";
    }

    @PostMapping("/GetAllDinamico")
    public String BusquedaDinamica(@ModelAttribute Usuario usuario, Model model) {
        Result result = usuarioDAOImplementation.GetAllDinamicoNombre(usuario);
        Result resultRol = rolDAOImplementation.GetAll();
        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.Rol = new Rol();

        model.addAttribute("roles", resultRol.object);
        model.addAttribute("listaUsuario", result.objects);
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
        return "UsuarioIndex";
    }

    @GetMapping("Form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {
        if (IdUsuario == 0) { // Agregar
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.Colonia = new Colonia();
            usuarioDireccion.Direccion.Colonia.Municipio = new Municipio();
            usuarioDireccion.Direccion.Colonia.Municipio.Estado = new Estado();
            usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais = new Pais();
            usuarioDireccion.Usuario.Rol = new Rol();

            model.addAttribute("paises", paisDAOImplementation.GetAll().object);
            model.addAttribute("roles", rolDAOImplementation.GetAll().object);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        } else { // Editar
            System.out.println("Voy a editar");
            Result result = usuarioDAOImplementation.DireccionesByIdUsuario(IdUsuario);
            model.addAttribute("usuarioDirecciones", result.object);
            return "UsuarioDetail";
        }
    }

    @GetMapping("/formEditable")
    public String FormEditable(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
        if (IdDireccion == null) { //Editar usuario
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.GetById(IdUsuario).object;
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("roles", rolDAOImplementation.GetAll().object);

        } else if (IdDireccion == 0) { //Agregar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(0);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().object);

        } else { //Editar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(IdDireccion);

            usuarioDireccion.Direccion = (Direccion) direccionDAOImplementation.DireccionesByIdDireccion(IdDireccion).object;
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().correct ? paisDAOImplementation.GetAll().object : null);
        } //Tus paises están en object y no en objects

        return "FormUsuario";
    }

    @PostMapping("Form")
    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindigResult, Model model) {

        if (usuarioDireccion.Usuario.getIdUsuario() == 0) { //Agregar
            System.out.println("Agregando usuario y direccion");
            usuarioDireccion.Usuario.setFechaNacimiento(new Date());
            usuarioDAOImplementation.Add(usuarioDireccion);
        } else {
            if (usuarioDireccion.Direccion.getIdDireccion() == -1) { //Editar usuario
                usuarioDAOImplementation.UsuarioUpdate(usuarioDireccion.Usuario);
            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) { //Agregar direccion
                System.out.println("Agregando direccion");
            } else {
                usuarioDAOImplementation.DireccionUpdate(usuarioDireccion);
                System.out.println("Editando direccion");//Editar direccion
            }
        }

        return "redirect:/Usuario";
    }

    @GetMapping("EstadoByIdPais/{IdPais}")
    @ResponseBody
    public Result EstadoByIdPais(@PathVariable int IdPais) {
        Result result = estadoDAOImplementation.EstadoByIdPais(IdPais);

        return result;
    }

    @GetMapping("MunicipioByIdEstado/{IdEstado}")
    @ResponseBody
    public Result MunicipioByIdEstado(@PathVariable int IdEstado) {
        Result result = municipioDAOImplementation.MunicipioByIdEstado(IdEstado);

        return result;
    }

    @GetMapping("ColoniaByIdMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result ColoniaByIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipio(IdMunicipio);

        return result;
    }

    @GetMapping("ColoniaByCodigoPostal/{CodigoPostal}")
    @ResponseBody
    public Result ColoniaByCodigoPostal(@PathVariable String CodigoPostal) {
        Result result = coloniaDAOImplementation.ColoniaByCodigoPostal(CodigoPostal);

        return result;
    }
}
