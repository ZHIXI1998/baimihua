package com.hamster.baimihua.controller;

import com.hamster.baimihua.entity.Ingredient;
import com.hamster.baimihua.entity.Taco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1.@SessionAttributes:SpringMVC提供的注解，将ModelMap的属性值共享到session中。
 * 2.@ModelAttribute:
 * 2.1:在同一个Controller中，标注了@ModelAttribute的方法实际上会在@RequestMapping方法之前被调用，
 * 因此对于一个controller映射多个URL的用法来说，要谨慎使用
 * 2.2:一个Controller可以拥有多个@ModelAttribute方法，
 * 同一个Controller内的这些方法都会在@RequestMapping方法之前被调用；
 * 2.3:@ModelAttribute标注的方法也可以定义在@ControllerAdvice标注的类中，
 * 并且这些@ModelAttribute可以同时对多个控制器生效。
 */
@Controller
@RequestMapping("/ ")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private static Logger log = LoggerFactory.getLogger(DesignTacoController.class);

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                //面粉卷
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                //玉米饼
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                //碎牛肉
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                //猪肩肉
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                //西红柿丁
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                //莴苣
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                //切达奶酪
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                //杰克奶酪
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                //萨尔萨辣酱（Salsa）是墨西哥菜肴中常用的烹调和佐餐酱料，一般用番茄和辣椒制成。
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                //酸奶油
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));

        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }
    @GetMapping
    public String showDesignForm(){
        return "design";
    }
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
