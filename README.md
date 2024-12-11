# On The Brink
A simple event management system where users can create, view, and register for events at Quevera.

## Installation
First make a fork of this repo. Click fork at the top of the screen. After, you can clone the repo using
```
git clone git@github.com:{YOUR_USERNAME}/On-The-Brink.git
```
If you are not able to clone, it may be because you have not added your SSH key to gitlab. If so, go to SSH Key Setting

Then next thing after you clone the repo is to link the main repository.
```
cd web
git remote add blessed git@github.com:ChromeSkullex/On-The-Brink.git
```
This allows your local git instance to connect to the main repo.


## Contributing

1. Create a branch after your named ticket number. `git checkout -b {branch-name}` (`-b` creates a new branch) 
2. Do your changes. 
2. Review your changes
2. Stash your changes once everything is reviewed.
3. Commit with a detailed message on what you changed/added/removed.
4. Push your commited changes to your git repo by `git push origin {branch-name}`


## Pulling changes (Rebase)
If a new change have been made to the main repo, do these steps.
1. Ensure you are in your main branch `git checkout master`
2. Pull from the main repo by doing: `git pull blessed master`
3. Go to the branch you need to rebase by doing: `git rebase master`
