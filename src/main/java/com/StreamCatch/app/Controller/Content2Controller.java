package com.StreamCatch.app.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.StreamCatch.app.Entity.Content;
import com.StreamCatch.app.Entity.Content2;
import com.StreamCatch.app.Interfaces.ErrorHandler;
import com.StreamCatch.app.Service.Content2Service;
import com.StreamCatch.app.Service.ContentService;
@Controller
public class Content2Controller implements ErrorHandler {
 @Autowired
 private Content2Service service;
 @RequestMapping(path = {"/","/search"})
 public String home(Content2 Content2, Model model, String keyword) {
  if(keyword!=null) {
   List<Content2> list = service.getByKeyword(keyword);
   model.addAttribute("list", list);
  }else {
  List<Content2> list = service.getAllShops();
  model.addAttribute("list", list);}
  return "content";
 }
 

	

	
////////////////////////////////CREAR CONTENIDO ///////////////////////////////////	
	
	
// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
@GetMapping("/create")
public String addContent() {

return "createContent";

}


// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
@PostMapping("/create")
public String runCreate(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {

try {

service.createContent(file, name);
return "redirect:/content/list";

} catch (Exception e) {
model.put("error", e.getMessage());
return "createContent";
}

}


/////////////////////////////// MODIFICAR CONTENIDO //////////////////////////////////////


// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
@GetMapping("/update/{id}")
public String update(ModelMap model, @PathVariable("id") String id) {
try {
model.addAttribute("content2", service.findById(id));

} catch (Exception e) {
return this.errorHandler(e, model);
}

return "modContent";
}


// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
@PostMapping("/update/{id}")
public String updateContent(ModelMap model, @RequestParam("file") MultipartFile file, 
@RequestParam("name") String name, @PathVariable("id") String id) {


try {

service.updateContent(file, name, id);

} catch (Exception e) {

this.errorHandler(e, model);
}

return "redirect:/content/list";
}


//////////////////////////////////// ELIMINAR CONTENIDO /////////////////////////////////////


// @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //
@GetMapping("/remove/{id}")
public String remove(ModelMap model, @PathVariable("id") String id) {

try {
service.removeById(id);
return "redirect:/content/list";

} catch (Exception e) {
return this.errorHandler(e, model);
}
}


@Override
public String errorHandler(Exception e, ModelMap model) {
	// TODO Auto-generated method stub
	return null;
}
}