import streamlit as st
import streamlit.components.v1 as components

my_component = components.declare_component("my_component", path="frontend/public")

num_clicks = my_component(default=0)
st.markdown("You've clicked %s times!" % int(num_clicks))
