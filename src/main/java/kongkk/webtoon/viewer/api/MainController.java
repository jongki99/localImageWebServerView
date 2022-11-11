package kongkk.webtoon.viewer.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kongkk.webtoon.viewer.api.service.DirUtilService;
import kongkk.webtoon.viewer.api.service.OrderEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {
	
	private static final String HITOMI_DOWN_FOLDER_NAME = "hitomi_downloaded";
	
	@Autowired
	private DirUtilService dirUtilService;

	@RequestMapping(value = "/")
	public String test(Model model) {
		return "frameset";
	}

	@RequestMapping("/cate.do")
	public String cate(Model model) throws UnsupportedEncodingException {
		List<Dir> dirs = new ArrayList<>();
		
		for (BaseData baseData : BaseData.dataMapList) {
			dirs.add(new Dir(baseData.getBaseName()));
		}
		List<Dir2> dirList = new ArrayList<>();
		log.debug("dirs = size={}, {}", dirs.size(), dirs);
		
		for (Dir dir : dirs) {
			log.debug("dir info : {}", dir);
			File[] getRootList = dir.getRootList();
			
			for ( File file : getRootList ) {
				String dirName = file.getName();
				log.debug("dirName = {}", dirName);
				
				if ( dirName.length() >= HITOMI_DOWN_FOLDER_NAME.length() && HITOMI_DOWN_FOLDER_NAME.equals(dirName.substring(0, HITOMI_DOWN_FOLDER_NAME.length() )) ) {
					String lastDate = dirUtilService.getDateString(file);
					String cat = "";
					if ( ! HITOMI_DOWN_FOLDER_NAME.equals(dirName) ) {
						cat = dirName.replace(HITOMI_DOWN_FOLDER_NAME+"_", "");
					}
					dirList.add(
							Dir2.builder()
							.baseName(dir.baseName)
							.cat(cat)
							.url(URLEncoder.encode(dirName, "utf8"))
							.name(dirName)
							.lastDate(lastDate)
							.build()
							);
				}
			}
		}
		log.debug("dirList.size = {}, dirList = {}", dirList.size(), dirList);
		model.addAttribute("dirList", dirList);
		return "cate";
	}

	@RequestMapping("/list.do")
	public String list(ModelMap model, @ModelAttribute Dir param) throws UnsupportedEncodingException {
		log.debug("model, param : {}, {}", model, param);
		
		List<Dir2> dirList = new ArrayList<>();
		for (File file : param.getFiles(OrderEnum.UPDATE_DATE_DESC)) {
			String dirName = file.getName();
			String lastDate = dirUtilService.getDateString(file);
			dirList.add(
					Dir2.builder()
					.baseName(param.baseName)
					.url(URLEncoder.encode(dirName, "utf8"))
					.name(dirName)
					.lastDate(lastDate)
					.build()
			);
		}

		model.addAttribute("param2", param);
		model.addAttribute("dirList", dirList);
		return "list";
	}

	@RequestMapping("/sub-list.do")
	public String subList(ModelMap model, @ModelAttribute Dir param) throws UnsupportedEncodingException {
		log.debug("model, param : {}, {}", model, param);

		List<Dir2> dirList = dirUtilService.getListSubName(param);

		model.addAttribute("param2", param);
		model.addAttribute("dirSubList", dirList);
		return "sub-list";

	}

	@RequestMapping("/main.do")
	public String main(ModelMap model, @ModelAttribute Dir param) throws UnsupportedEncodingException {
		param.listName = URLDecoder.decode(param.listName, StandardCharsets.UTF_8.toString());
		log.info("main.do model, param : {}, {}", model, param);

		List<Dir2> imgList = new ArrayList<>();
		for (File file : param.getFiles(OrderEnum.FILE_NAME_ASC)) {
			String dirName = file.getName();
			String imageFullPath = "file:"
					+ param.getFullImagePath()
					+ File.separatorChar
					+ URLEncoder.encode(dirName, "utf8")
			;
			imageFullPath = imageFullPath.replaceAll("\\\\", "/");
			log.debug("dirName, imageFullPath : {}, {}", dirName, imageFullPath);
			imgList.add(
					Dir2.builder()
						.baseName(param.baseName)
						.url(URLEncoder.encode(dirName, "utf8"))
						.name(dirName)
						.imageFullPath(imageFullPath)
						.build()
					);
		}

		model.addAttribute("param2", param);
		model.addAttribute("imgList", imgList);
		model.addAttribute("parentName", param.getListSubName());
		model.addAttribute("prevDir", dirUtilService.getListSubPrevDir(param));
		model.addAttribute("nextDir", dirUtilService.getListSubNextDir(param));

		return "main";
	}
	
	
	@RequestMapping("/main2.do")
	public String main2(ModelMap model, @ModelAttribute Dir param) throws UnsupportedEncodingException {
		log.debug("main2.do model, param : {}, {}", model, param);
		
		List<Dir2> imgList = new ArrayList<>();
		for (File file : param.getFiles(OrderEnum.FILE_NAME_ASC)) {
			String dirName = file.getName();
			String imageFullPath = "file:"
					+ param.getFullImagePath()
					+ File.separatorChar
					+ URLEncoder.encode(dirName, "utf8")
					;
			imageFullPath = imageFullPath.replaceAll("\\\\", "/");
			log.debug("dirName, imageFullPath : {}, {}", dirName, imageFullPath);
			imgList.add(
					Dir2.builder()
					.baseName(param.baseName)
					.url(URLEncoder.encode(dirName, "utf8"))
					.name(dirName)
					.imageFullPath(imageFullPath)
					.build()
					);
		}
		
		model.addAttribute("param2", param);
		model.addAttribute("imgList", imgList);
		model.addAttribute("parentName", param.getListSubName());
		
		return "main2";
	}
	
	@GetMapping("/download.do")
	public ResponseEntity<Resource> download(ModelMap model, @ModelAttribute Dir param) throws IOException {
		//param.getBasePath();
		Path path = Paths.get(param.getFullImagePath());
		String contentType = Files.probeContentType(path);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);

		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

}
