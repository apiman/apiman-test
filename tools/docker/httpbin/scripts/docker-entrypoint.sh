#!/usr/bin/env bash


# always upgrade to actual version
pip install --upgrade git+https://github.com/wermington/httpbin.git

# start server
gunicorn -b :5000 httpbin:app

