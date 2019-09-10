package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import com.iflytek.lingxi.common.util.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${package.Service}.${table.serviceName};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/api/v1<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${entity}Service ${entity?uncap_first}Service;

    /**
    * 获取数据分页列表
    *
    * @param page 分页参数
    */
    @GetMapping("/page")
    public ResponseEntity<Result> pageList(Page page){
        return ResponseEntity.ok(Result.success(${entity?uncap_first}Service.page(page)));
    }

    /**
    * 根据ID查找数据
    *
    * @param id 主键
    */
    @GetMapping("/{id}")
    public ResponseEntity<Result> findById(@PathVariable Integer id){
        return ResponseEntity.ok(Result.success(${entity?uncap_first}Service.getById(id)));
    }

    /**
    * 添加数据
    */
    @PostMapping
    public ResponseEntity<Result> addOne(@RequestBody ${entity} ${entity?uncap_first}){
        return ResponseEntity.ok(Result.success(${entity?uncap_first}Service.save(${entity?uncap_first})));
    }

    /**
    * 更新数据
    */
    @PutMapping
    public ResponseEntity<Result> updateOne(@RequestBody ${entity} ${entity?uncap_first}){
        return ResponseEntity.ok(Result.success(${entity?uncap_first}Service.updateById(${entity?uncap_first})));
    }

    /**
    * 删除数据
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteItems(@PathVariable Integer id){
        return ResponseEntity.ok(Result.success(${entity?uncap_first}Service.removeById(id)));
    }

}
</#if>
