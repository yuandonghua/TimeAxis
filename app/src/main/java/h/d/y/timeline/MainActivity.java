package h.d.y.timeline;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * @description:时光轴效果
 * @author:袁东华 created at 2016/7/25 0025 上午 11:36
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TimeLineAdapter timeLineAdapter;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        timeLineAdapter = new TimeLineAdapter(this);
        //添加header
        View view = LayoutInflater.from(this).inflate(R.layout.header_create_company_process, recyclerView, false);
        timeLineAdapter.setHeaderView(view);
        recyclerView.setAdapter(timeLineAdapter);
    }
    private void initData() {
        list.add("");
        list.add("核名\n核定你注册公司名称");
        list.add("申请登记\n申请登记,取得营业执照");
        list.add("刻章\n制作并备案公章,财务专用章等");
        list.add("税务报到\n到税务部门填报信息,取得纳税授权一证通");
        list.add("银行开设基本户\n到银行开设公司基本户,取得开户许可证");
        list.add("社保开户\n到社保主管部门确定社保登记证");
        timeLineAdapter.setList(list);

    }
}
