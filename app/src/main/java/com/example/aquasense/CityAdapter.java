package com.example.aquasense;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aquasense.R;
import com.example.aquasense.model.City;
import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<City> cities;
    private List<City> filteredCities;

    public CityAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
        this.filteredCities = new ArrayList<>(cities);
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = filteredCities.get(position);
        holder.cityId.setText(city.getCityId());
        holder.textViewCityName.setText(city.getName());
        holder.textViewWaterQuality.setText(city.getWaterQuality());
        Glide.with(context)
                .load(city.getImageResource())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                .into(holder.imageViewCity);

        holder.cardViewCity.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("city_id", city.getCityId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCity;
        TextView textViewCityName, cityId;
        TextView textViewWaterQuality;
        CardView cardViewCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityId = itemView.findViewById(R.id.cityId);
            imageViewCity = itemView.findViewById(R.id.imageViewCity);
            textViewCityName = itemView.findViewById(R.id.textViewCityName);
            textViewWaterQuality = itemView.findViewById(R.id.textViewWaterQuality);
            cardViewCity = itemView.findViewById(R.id.cardViewCity);
        }
    }

    // Implement Filterable interface methods
    @Override
    public Filter getFilter() {
        return cityFilter;
    }

    private Filter cityFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<City> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cities);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (City city : cities) {
                    if (city.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(city);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredCities.clear();
            filteredCities.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}