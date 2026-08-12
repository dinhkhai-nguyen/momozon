package server.api;

import commons.Name;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.NameRepository;
import server.service.CounterService;

@Controller
@RequestMapping("/")
public class SomeController {

    private CounterService counter;
    private NameRepository db;

    public SomeController(CounterService counter, NameRepository db) {
        this.counter = counter;
        this.db = db;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public String name(@PathVariable("name") String name, @RequestParam(name = "title", required = false) String title) {
        var n = new Name();
        n.name = name;
        db.save(n);

        var sb = new StringBuilder("Hello ");
        if (title != null) {
            sb.append(title).append(' ');
        }
        sb.append(name);
        sb.append("!");
        sb.append(" You are visitor #").append(counter.getAddIncrease());
        return sb.toString();
    }
}